import re
import os

pattern = re.compile(r'^\s+(?!this\b)(\w+(?:\.\w+){2,})', re.MULTILINE)

matches = set()
for root, dirs, files in os.walk('modules'):
    for f in files:
        if f.endswith('.java'):
            with open(os.path.join(root, f), 'r') as file:
                content = file.read()
                for match in pattern.findall(content):
                    matches.add(match)

print("Found patterns:")
for m in sorted(list(matches))[:50]:
    print(m)
