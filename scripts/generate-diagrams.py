#!/usr/bin/env python3
"""Generate a code-derived class view and migration-derived EER, without network access."""
from pathlib import Path
import re

root = Path(__file__).resolve().parents[1]
output = root / 'docs/assets/plantuml-code'
output.mkdir(parents=True, exist_ok=True)
sources = list((root / 'backend/src/main/java/com/eatinghalal').rglob('*.java'))
classes = {}
for source in sources:
    code = source.read_text()
    match = re.search(r'public (?:final )?(class|interface) (\w+)', code)
    if match:
        kind, name = match.groups()
        if source.parent.name in ('model', 'service', 'repository', 'web', 'integration') and name not in ('ApiModels', 'ApiExceptionHandler'):
            classes[name] = (kind, source.parent.name, code)

lines = ['@startuml', '!pragma layout smetana', 'skinparam classAttributeIconSize 0', 'skinparam shadowing false', 'title EatingHalal — classes extracted from production source', 'package "View — React presentation" #EDF2DB {', '  class "App (React component)" as ReactApp <<component>>', '  class "Entry (React component)" as ReactEntry <<component>>', '}', 'ReactApp --> ReactEntry : renders']
for package, label, color in [('web','Controller — REST request handling','#E7EFF8'),('service','Model — application services','#EDF2DB'),('model','Model — domain / persistent entities','#EDF2DB'),('repository','Model — framework-generated repositories','#EDF2DB'),('integration','Model — product-source integration','#EDF2DB')]:
    lines.append(f'package "{label}" {color} {{')
    for name, (kind, location, code) in classes.items():
        if location != package:
            continue
        lines.append(f'  {kind} {name} {{')
        for visibility, datatype, field in re.findall(r'\b(public|private)\s+(?:final\s+)?([\w<>?, ]+)\s+(\w+)\s*;', code):
            if '(' not in datatype:
                lines.append(f'    {"+" if visibility == "public" else "-"} {field}: {datatype.strip()}')
        methods = re.findall(r'public (?:synchronized )?(?:static )?(?:[\w<>?, ]+)\s+(\w+)\s*\(', code)
        for method in dict.fromkeys(methods):
            lines.append(f'    + {method}()')
        lines.append('  }')
    lines.append('}')
for name, (kind, package, code) in classes.items():
    fields = re.findall(r'\b(?:public|private)\s+(?:final\s+)?(\w+)\s+\w+\s*;', code)
    for dependency in dict.fromkeys(fields):
        if dependency in classes:
            if package == 'model':
                lines.append(f'{dependency} "1" -- "0..*" {name}')
            else:
                lines.append(f'{name} ..> {dependency} : uses')
    for contract in re.findall(r'implements (\w+)', code):
        if contract in classes:
            lines.append(f'{name} ..|> {contract}')
lines += ['ReactApp ..> ProductController : HTTP', 'ReactApp ..> ShoppingListController : HTTP', 'ReactApp ..> AdminAssessmentController : HTTP', 'ReactApp ..> SessionController : HTTP', 'ReactApp ..> PhotoController : HTTP', 'note bottom', 'Classes, fields and dependency types are extracted from Java source.', 'React component names are extracted from the frontend design.', 'Method parameter detail and framework classes are omitted for readability.', 'end note', '@enduml']
(output / 'SAD-ClassDiagram.puml').write_text('\n'.join(lines) + '\n')

sql = (root / 'backend/src/main/resources/db/migration/V1__initial_schema.sql').read_text()
lines = ['@startuml', '!pragma layout smetana', 'skinparam shadowing false', 'title EatingHalal — PostgreSQL schema from Flyway migration']
relations = []
for table, body in re.findall(r'CREATE TABLE (\w+) \((.*?)\);', sql, re.S):
    lines.append(f'entity {table} {{')
    # Split columns only at top-level commas; CHECK and UNIQUE also contain parentheses.
    chunks, depth, start = [], 0, 0
    for index, character in enumerate(body):
        depth += (character == '(') - (character == ')')
        if character == ',' and depth == 0:
            chunks.append(body[start:index].strip()); start = index + 1
    chunks.append(body[start:].strip())
    for column in chunks:
        match = re.match(r'(\w+)\s+(UUID|VARCHAR\(\d+\)|TEXT|INTEGER|TIMESTAMP WITH TIME ZONE)', column)
        if not match:
            continue
        field, datatype = match.groups()
        marker = ' <<PK>>' if 'PRIMARY KEY' in column else ' <<FK>>' if 'REFERENCES' in column else ''
        required = '* ' if 'NOT NULL' in column or 'PRIMARY KEY' in column else ''
        lines.append(f'  {required}{field}: {datatype}{marker}')
        target = re.search(r'REFERENCES (\w+)\(id\)', column)
        if target:
            relations.append(f'{target.group(1)} ||--o{{ {table} : {field}')
    lines.append('}')
lines += relations + ['note bottom', 'Unique: username; barcode; (product_id, version); (owner_id, product_id).', 'Quantity: 1..999. No cascading delete of shared products or assessments.', 'The migration is applied and JPA validates the database schema.', 'end note', '@enduml']
(output / 'SAD-DatabaseModel.puml').write_text('\n'.join(lines) + '\n')
print(f'Generated class view for {len(classes)} Java classes/interfaces and database model.')
