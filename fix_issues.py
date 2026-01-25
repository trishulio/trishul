#!/usr/bin/env python3
import json
import os
import re

def extract_file_path(component):
    """Extract file path from component string"""
    # Format: trishulio_trishul_...:modules/...
    if ':' in component:
        return component.split(':', 1)[1]
    return None

def read_issues_from_page(page_num):
    """Read issues from a specific page file"""
    with open(f'issues_page_{page_num}.json', 'r') as f:
        data = json.load(f)
        return data['issues']

def fix_s5786_public_modifier(file_path, line_num):
    """Fix java:S5786 - Remove public modifier from JUnit 5 test methods"""
    full_path = f'/Users/rishabmanocha/SourceCode/trishul/{file_path}'
    if not os.path.exists(full_path):
        return False
    
    with open(full_path, 'r') as f:
        lines = f.readlines()
    
    if line_num > len(lines):
        return False
    
    # Line numbers are 1-indexed
    line_idx = line_num - 1
    original_line = lines[line_idx]
    
    # Remove 'public ' from the line
    if 'public ' in original_line:
        lines[line_idx] = original_line.replace('public ', '', 1)
        
        with open(full_path, 'w') as f:
            f.writelines(lines)
        return True
    
    return False

def fix_s1612_lambda_to_method_ref(file_path, line_num, message):
    """Fix java:S1612 - Replace lambda with method reference"""
    full_path = f'/Users/rishabmanocha/SourceCode/trishul/{file_path}'
    if not os.path.exists(full_path):
        return False
    
    try:
        with open(full_path, 'r') as f:
            lines = f.readlines()
        
        if line_num > len(lines):
            return False
        
        line_idx = line_num - 1
        original_line = lines[line_idx]
        
        # Extract method reference from message like "Replace this lambda with method reference 'Identified::getId'."
        match = re.search(r"method reference ['\"]([^'\"]+)['\"]", message)
        if not match:
            return False
        
        method_ref = match.group(1)
        
        # Look for lambda patterns like "x -> x.getId()" or "item -> item.getId()"
        # Replace with method reference
        lambda_patterns = [
            (r'\w+\s*->\s*\w+\.\w+\(\)', method_ref),
            (r'\(\s*\w+\s*\)\s*->\s*\w+\.\w+\(\)', method_ref),
        ]
        
        modified = False
        for pattern, replacement in lambda_patterns:
            if re.search(pattern, original_line):
                lines[line_idx] = re.sub(pattern, replacement, original_line)
                modified = True
                break
        
        if modified:
            with open(full_path, 'w') as f:
                f.writelines(lines)
            return True
    except Exception:
        pass
    
    return False

def fix_s1488_immediate_return(file_path, line_num, message):
    """Fix java:S1488 - Immediately return expression instead of temporary variable"""
    full_path = f'/Users/rishabmanocha/SourceCode/trishul/{file_path}'
    if not os.path.exists(full_path):
        return False
    
    try:
        with open(full_path, 'r') as f:
            lines = f.readlines()
        
        if line_num > len(lines) or line_num < 2:
            return False
        
        line_idx = line_num - 1
        
        # Extract variable name from message
        match = re.search(r'variable ["\'](\w+)["\']', message)
        if not match:
            return False
        
        var_name = match.group(1)
        
        # Check if current line assigns to variable
        current_line = lines[line_idx].strip()
        if f'{var_name} =' in current_line or f'{var_name}=' in current_line:
            # Check if next line returns the variable
            if line_num < len(lines):
                next_line = lines[line_idx + 1].strip()
                if next_line == f'return {var_name};':
                    # Extract the assignment value
                    match_assign = re.search(rf'{var_name}\s*=\s*(.+);', current_line)
                    if match_assign:
                        value = match_assign.group(1)
                        # Replace both lines with direct return
                        indent = len(lines[line_idx]) - len(lines[line_idx].lstrip())
                        lines[line_idx] = ' ' * indent + f'return {value};\n'
                        lines.pop(line_idx + 1)
                        
                        with open(full_path, 'w') as f:
                            f.writelines(lines)
                        return True
    except Exception:
        pass
    
    return False

def fix_s1481_unused_variable(file_path, line_num, message):
    """Fix java:S1481 - Remove unused local variable"""
    # This requires understanding if the variable assignment has side effects
    # Skip for safety
    return False

def fix_s1905_unnecessary_cast(file_path, line_num, message):
    """Fix java:S1905 - Remove unnecessary cast"""
    full_path = f'/Users/rishabmanocha/SourceCode/trishul/{file_path}'
    if not os.path.exists(full_path):
        return False
    
    try:
        with open(full_path, 'r') as f:
            lines = f.readlines()
        
        if line_num > len(lines):
            return False
        
        line_idx = line_num - 1
        original_line = lines[line_idx]
        
        # Extract cast type from message
        match = re.search(r'cast to ["\']([^"\']+)["\']', message)
        if not match:
            return False
        
        cast_type = match.group(1)
        
        # Remove casts like (CastType) expression or (CastType)expression
        pattern = rf'\(\s*{re.escape(cast_type)}\s*\)\s*'
        if re.search(pattern, original_line):
            lines[line_idx] = re.sub(pattern, '', original_line)
            
            with open(full_path, 'w') as f:
                f.writelines(lines)
            return True
    except Exception:
        pass
    
    return False

def fix_s1116_empty_statement(file_path, line_num):
    """Fix java:S1116 - Remove empty statement"""
    full_path = f'/Users/rishabmanocha/SourceCode/trishul/{file_path}'
    if not os.path.exists(full_path):
        return False
    
    try:
        with open(full_path, 'r') as f:
            lines = f.readlines()
        
        if line_num > len(lines):
            return False
        
        line_idx = line_num - 1
        original_line = lines[line_idx]
        
        # Remove standalone semicolons
        if original_line.strip() == ';':
            lines.pop(line_idx)
            
            with open(full_path, 'w') as f:
                f.writelines(lines)
            return True
        
        # Remove double semicolons
        if ';;' in original_line:
            lines[line_idx] = original_line.replace(';;', ';')
            
            with open(full_path, 'w') as f:
                f.writelines(lines)
            return True
    except Exception:
        pass
    
    return False

def process_page(page_num):
    """Process all issues from a specific page"""
    issues = read_issues_from_page(page_num)
    
    print(f'\n=== Processing Page {page_num} ({len(issues)} issues) ===')
    
    # Group issues by type
    issues_by_rule = {}
    for issue in issues:
        rule = issue['rule']
        if rule not in issues_by_rule:
            issues_by_rule[rule] = []
        issues_by_rule[rule].append(issue)
    
    stats = {'fixed': 0, 'skipped': 0, 'failed': 0}
    
    # Define rules and their fix functions
    fix_functions = {
        'java:S5786': lambda issue: fix_s5786_public_modifier(
            extract_file_path(issue['component']), issue.get('line')
        ),
        'java:S1612': lambda issue: fix_s1612_lambda_to_method_ref(
            extract_file_path(issue['component']), issue.get('line'), issue['message']
        ),
        'java:S1488': lambda issue: fix_s1488_immediate_return(
            extract_file_path(issue['component']), issue.get('line'), issue['message']
        ),
        'java:S1905': lambda issue: fix_s1905_unnecessary_cast(
            extract_file_path(issue['component']), issue.get('line'), issue['message']
        ),
        'java:S1116': lambda issue: fix_s1116_empty_statement(
            extract_file_path(issue['component']), issue.get('line')
        ),
    }
    
    # Process each rule type
    for rule, fix_func in fix_functions.items():
        if rule in issues_by_rule:
            rule_issues = issues_by_rule[rule]
            print(f'\nFixing {rule} issues ({len(rule_issues)} issues)...')
            fixed_count = 0
            for issue in rule_issues:
                file_path = extract_file_path(issue['component'])
                if file_path and 'line' in issue:
                    try:
                        if fix_func(issue):
                            fixed_count += 1
                            stats['fixed'] += 1
                            if fixed_count % 50 == 0:
                                print(f'  Fixed {fixed_count} {rule} issues...')
                        else:
                            stats['failed'] += 1
                    except Exception as e:
                        stats['failed'] += 1
    
    print(f'\nPage {page_num} Results:')
    print(f'  Fixed: {stats["fixed"]}')
    print(f'  Failed: {stats["failed"]}')
    print(f'  Skipped: {len(issues) - stats["fixed"] - stats["failed"]}')
    
    return stats

# Process page 1
if __name__ == '__main__':
    import sys
    page_start = int(sys.argv[1]) if len(sys.argv) > 1 else 1
    page_end = int(sys.argv[2]) if len(sys.argv) > 2 else 1
    
    total_stats = {'fixed': 0, 'failed': 0}
    for page_num in range(page_start, page_end + 1):
        stats = process_page(page_num)
        total_stats['fixed'] += stats['fixed']
        total_stats['failed'] += stats['failed']
    
    print(f'\n=== Overall Stats for Pages {page_start}-{page_end} ===')
    print(f'Fixed: {total_stats["fixed"]}')
    print(f'Failed: {total_stats["failed"]}')
