import os
import re

pattern = re.compile(r'^(?!(package|import)).* (\w+\.){3}')

results = []
for root, dirs, files in os.walk('.'):
    if '.git' in root or 'target' in root:
        continue
    for file in files:
        if file.endswith('.java'):
            filepath = os.path.join(root, file)
            with open(filepath, 'r') as f:
                lines = f.readlines()
            for i, line in enumerate(lines):
                if pattern.search(line):
                    results.append((filepath, i+1, line.strip()))

for filepath, line_num, line in results:
    print(f"{filepath}:{line_num}: {line}")

print(f"Total found: {len(results)}")
