#!/usr/bin/env python3
"""Check local links and the project's use-case documentation requirements."""
from pathlib import Path
import re
from urllib.parse import unquote

root = Path(__file__).resolve().parents[1]
failures = []
def fail(path, text, offset, message):
    line = text.count('\n', 0, offset) + 1
    failures.append(f'{path.relative_to(root)}:{line}: {message}')

documents = [root / 'README.md', *sorted((root / 'docs').rglob('*.md'))]
for document in documents:
    text = document.read_text()
    for match in re.finditer(r'\]\(([^)]+)\)', text):
        link = match.group(1)
        if '://' in link or link.startswith('#'):
            continue
        target = (document.parent / unquote(link.split('#')[0])).resolve()
        if not target.exists():
            fail(document, text, match.start(), 'Missing local link target: ' + link)

specifications = sorted((root / 'docs/use-cases').glob('UC*.md'))
for document in specifications:
    text = document.read_text()
    headings = ['## 1. Brief Description', '## 2. Flow of Events', '### 2.1 Basic Flow', '### 2.2 Alternative Flows', '### Activity Diagram', '## 3. Special Requirements', '## 4. Preconditions', '## 5. Postconditions', '## 6. Extension Points']
    positions = [text.find(heading) for heading in headings]
    if -1 in positions or positions != sorted(positions):
        fail(document, text, 0, 'Incorrect/missing use-case section order')
    if '[Activity Diagram](#activity-diagram)' not in text.split('## 1. Brief Description')[0]:
        fail(document, text, 0, 'Activity diagram missing from table of contents')
    for extension, folder in [('puml', 'plantuml-code'), ('png', 'images')]:
        if not (root / 'docs/assets' / folder / (document.stem + '.' + extension)).exists():
            fail(document, text, 0, 'Missing matching diagram asset: ' + extension)
    for forbidden in ['## Revision History', '**Document identifier:**', '**Version:**', '**Date:**', '## 1. Use-Case Name']:
        if forbidden in text:
            fail(document, text, text.find(forbidden), 'Forbidden header: ' + forbidden)
    if not re.fullmatch(r'UC\d{2}-[a-z0-9-]+\.md', document.name):
        fail(document, text, 0, 'Unexpected use-case filename')
    activity = text.split('### Activity Diagram', 1)[-1].split('## 3. Special Requirements', 1)[0]
    if '[PlantUML source]' not in activity or '[Cucumber feature]' not in activity:
        fail(document, text, text.find('### Activity Diagram'), 'Activity block must link source and feature')

tick = chr(96)
for document in [root/'docs/SoftwareRequirementsSpecification.md', root/'docs/use-cases/README.md', *specifications]:
    text = document.read_text()
    plain = re.sub(tick*3+'.*?'+tick*3+r'|~~~.*?~~~', lambda m:' ' * len(m.group()), text, flags=re.S)
    plain = re.sub(r'!?\[[^\]]*\]\([^)]*\)', lambda m:' ' * len(m.group()), plain)
    plain = re.sub(tick+'[^'+tick+']*'+tick, lambda m:' ' * len(m.group()), plain)
    for match in re.finditer(r'\bUC0[1-5]\b', plain):
        fail(document, text, match.start(), 'Use-case reference is not a hyperlink')
    for match in re.finditer(r'\bstep \d+ of (?:the basic flow|alternative flow [\d.]+)|\balternative flow 2\.2\.\d+', plain, flags=re.I):
        fail(document, text, match.start(), 'Flow/step reference is not a hyperlink')

for old in ['docs/EatingHalal_UseCases.puml', 'docs/use-cases/activity-diagrams', 'docs/use-cases/rup']:
    if (root/old).exists():
        failures.append(old+':1: obsolete asset location remains')
if failures:
    print('\n'.join('FAIL '+failure for failure in failures))
    raise SystemExit(1)
print(f'PASS: local links in {len(documents)} documents; section order, assets, references and filenames for {len(specifications)} use-case specifications.')
