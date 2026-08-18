import os
import re

# We want to match fully qualified names like java.util.List, but avoid false positives like string.split(".").
# Typical FQN in java starts with a lowercase word (package), followed by more words separated by dots, 
# ending with an Uppercase word (Class name).
# The user's regex is: ^(?!(package|import)).* (\w+\.){3}
# Let's search for lines that contain (\w+\.){3}

pattern = re.compile(r'(\b[a-z]\w*(?:\.[a-z]\w*){2,}\.[A-Z]\w*)')

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
                if line.strip().startswith('import') or line.strip().startswith('package'):
                    continue
                matches = pattern.findall(line)
                if matches:
                    results.append((filepath, i+1, line.strip(), matches))

for filepath, line_num, line, matches in results:
    print(f"{filepath}:{line_num}: {line} -> {matches}")

print(f"Total found: {len(results)}")
