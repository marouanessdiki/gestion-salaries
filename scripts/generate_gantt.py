#!/usr/bin/env python3
"""
Gantt Chart Generator for Gestion Salaries Project
Generates SVG Gantt chart from YAML task definition
"""

import yaml
import datetime
import argparse
import os
from pathlib import Path

class GanttGenerator:
    def __init__(self, tasks_file="docs/gantt/tasks.yml"):
        self.tasks_file = tasks_file
        self.colors = {
            "Analysis & Design": "#FF6B6B",
            "Backend Development": "#4ECDC4", 
            "Frontend Development": "#45B7D1",
            "Testing & Integration": "#96CEB4",
            "Deployment & Documentation": "#FFEAA7"
        }
        
    def load_tasks(self):
        """Load tasks from YAML file"""
        with open(self.tasks_file, 'r', encoding='utf-8') as f:
            return yaml.safe_load(f)
    
    def calculate_dates(self, project_data):
        """Calculate actual dates for tasks"""
        start_date = datetime.datetime.strptime(project_data['project']['start_date'], '%Y-%m-%d')
        
        phases_with_dates = []
        for phase in project_data['phases']:
            phase_start = start_date + datetime.timedelta(weeks=phase['start_week'] - 1)
            phase_end = phase_start + datetime.timedelta(weeks=phase['duration'])
            
            phase_info = {
                'name': phase['name'],
                'start_date': phase_start,
                'end_date': phase_end,
                'duration': phase['duration'],
                'tasks': []
            }
            
            task_start = phase_start
            for task in phase.get('tasks', []):
                task_end = task_start + datetime.timedelta(weeks=task['duration'])
                phase_info['tasks'].append({
                    'name': task['name'],
                    'start_date': task_start,
                    'end_date': task_end,
                    'duration': task['duration'],
                    'assignee': task.get('assignee', 'Developer'),
                    'description': task.get('description', '')
                })
                task_start = task_end
            
            phases_with_dates.append(phase_info)
        
        return phases_with_dates
    
    def generate_svg(self, project_data, output_file="assets/diagrams/gantt.svg"):
        """Generate SVG Gantt chart"""
        phases = self.calculate_dates(project_data)
        
        # SVG dimensions
        width = 1200
        height = 600
        margin = 50
        chart_width = width - 2 * margin
        chart_height = height - 2 * margin
        
        # Calculate time span
        start_date = min(phase['start_date'] for phase in phases)
        end_date = max(phase['end_date'] for phase in phases)
        total_days = (end_date - start_date).days
        
        svg_content = f'''<?xml version="1.0" encoding="UTF-8"?>
<svg width="{width}" height="{height}" xmlns="http://www.w3.org/2000/svg">
  <style>
    .phase-bar {{ fill-opacity: 0.8; stroke: #333; stroke-width: 1; }}
    .phase-text {{ font-family: Arial, sans-serif; font-size: 12px; fill: #333; }}
    .title {{ font-family: Arial, sans-serif; font-size: 16px; font-weight: bold; fill: #333; }}
    .grid-line {{ stroke: #ddd; stroke-width: 0.5; }}
    .milestone {{ fill: #ff4757; stroke: #333; stroke-width: 1; }}
  </style>
  
  <!-- Title -->
  <text x="{width//2}" y="30" class="title" text-anchor="middle">
    {project_data['project']['name']} - Gantt Chart
  </text>
  
  <!-- Grid lines -->'''
        
        # Add month grid lines
        current_date = start_date
        while current_date <= end_date:
            x = margin + ((current_date - start_date).days / total_days) * chart_width
            svg_content += f'''
  <line x1="{x}" y1="{margin}" x2="{x}" y2="{height - margin}" class="grid-line"/>
  <text x="{x}" y="{margin - 10}" class="phase-text" text-anchor="middle">
    {current_date.strftime('%b %Y')}
  </text>'''
            current_date += datetime.timedelta(days=30)
        
        # Add phases
        y_pos = margin + 40
        bar_height = 30
        spacing = 50
        
        for i, phase in enumerate(phases):
            color = self.colors.get(phase['name'], '#95A5A6')
            
            # Calculate bar position
            start_x = margin + ((phase['start_date'] - start_date).days / total_days) * chart_width
            bar_width = ((phase['end_date'] - phase['start_date']).days / total_days) * chart_width
            
            svg_content += f'''
  <!-- {phase['name']} -->
  <rect x="{start_x}" y="{y_pos}" width="{bar_width}" height="{bar_height}" 
        fill="{color}" class="phase-bar"/>
  <text x="{margin - 10}" y="{y_pos + bar_height//2 + 4}" class="phase-text" text-anchor="end">
    {phase['name']}
  </text>
  <text x="{start_x + bar_width//2}" y="{y_pos + bar_height//2 + 4}" class="phase-text" 
        text-anchor="middle" fill="white">
    {phase['duration']}w
  </text>'''
            
            y_pos += spacing
        
        # Add milestones
        if 'milestones' in project_data:
            for milestone in project_data['milestones']:
                milestone_date = datetime.datetime.strptime(milestone['date'], '%Y-%m-%d')
                x = margin + ((milestone_date - start_date).days / total_days) * chart_width
                
                svg_content += f'''
  <!-- Milestone: {milestone['name']} -->
  <polygon points="{x},{margin + 20} {x+8},{margin + 35} {x},{margin + 50} {x-8},{margin + 35}" 
           class="milestone"/>
  <text x="{x}" y="{margin + 15}" class="phase-text" text-anchor="middle" font-size="10">
    {milestone['name']}
  </text>'''
        
        svg_content += '''
</svg>'''
        
        # Ensure output directory exists
        os.makedirs(os.path.dirname(output_file), exist_ok=True)
        
        with open(output_file, 'w', encoding='utf-8') as f:
            f.write(svg_content)
        
        print(f"Gantt chart generated: {output_file}")
    
    def generate_html_preview(self, project_data, output_file="docs/gantt/index.html"):
        """Generate HTML preview page"""
        html_content = f'''<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gantt Chart - {project_data['project']['name']}</title>
    <style>
        body {{ font-family: Arial, sans-serif; margin: 20px; background: #f5f5f5; }}
        .container {{ max-width: 1400px; margin: 0 auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }}
        .header {{ text-align: center; margin-bottom: 30px; }}
        .gantt-container {{ text-align: center; margin: 20px 0; }}
        .info-panel {{ display: grid; grid-template-columns: 1fr 1fr; gap: 20px; margin-top: 30px; }}
        .info-box {{ background: #f8f9fa; padding: 15px; border-radius: 5px; }}
        .info-box h3 {{ margin-top: 0; color: #333; }}
        .phase-list {{ list-style: none; padding: 0; }}
        .phase-list li {{ margin: 8px 0; padding: 8px; background: white; border-radius: 4px; border-left: 4px solid #007bff; }}
        .milestone-list {{ list-style: none; padding: 0; }}
        .milestone-list li {{ margin: 5px 0; padding: 5px; background: #fff3cd; border-radius: 4px; }}
        .export-btn {{ background: #007bff; color: white; padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; margin: 10px; }}
        .export-btn:hover {{ background: #0056b3; }}
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>{project_data['project']['name']}</h1>
            <p><strong>Methodology:</strong> {project_data.get('methodology', 'Waterfall')} | 
               <strong>Duration:</strong> {project_data['project']['duration_weeks']} weeks</p>
            <button class="export-btn" onclick="window.open('../../assets/diagrams/gantt.svg', '_blank')">
                View Full SVG
            </button>
            <button class="export-btn" onclick="downloadSVG()">
                Download SVG
            </button>
        </div>
        
        <div class="gantt-container">
            <object data="../../assets/diagrams/gantt.svg" type="image/svg+xml" width="100%" height="400">
                <p>Your browser does not support SVG. <a href="../../assets/diagrams/gantt.svg">Download the SVG file</a>.</p>
            </object>
        </div>
        
        <div class="info-panel">
            <div class="info-box">
                <h3>Project Phases</h3>
                <ul class="phase-list">'''
        
        for phase in project_data['phases']:
            html_content += f'''
                    <li><strong>{phase['name']}</strong> - {phase['duration']} weeks</li>'''
        
        html_content += '''
                </ul>
            </div>
            
            <div class="info-box">
                <h3>Key Milestones</h3>
                <ul class="milestone-list">'''
        
        if 'milestones' in project_data:
            for milestone in project_data['milestones']:
                html_content += f'''
                    <li><strong>{milestone['name']}</strong> - {milestone['date']}</li>'''
        
        html_content += f'''
                </ul>
            </div>
        </div>
        
        <div class="info-box" style="margin-top: 20px;">
            <h3>Project Supervision</h3>
            <p><strong>Supervisor:</strong> {project_data.get('supervision', {}).get('supervisor', 'N/A')}</p>
            <p><strong>Meeting Frequency:</strong> {project_data.get('supervision', {}).get('frequency', 'N/A')}</p>
        </div>
    </div>
    
    <script>
        function downloadSVG() {{
            const link = document.createElement('a');
            link.href = '../../assets/diagrams/gantt.svg';
            link.download = 'gantt-chart.svg';
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
        }}
    </script>
</body>
</html>'''
        
        with open(output_file, 'w', encoding='utf-8') as f:
            f.write(html_content)
        
        print(f"HTML preview generated: {output_file}")

def main():
    parser = argparse.ArgumentParser(description='Generate Gantt chart from YAML tasks')
    parser.add_argument('--tasks', default='docs/gantt/tasks.yml', help='Path to tasks YAML file')
    parser.add_argument('--output-svg', default='assets/diagrams/gantt.svg', help='Output SVG file path')
    parser.add_argument('--output-html', default='docs/gantt/index.html', help='Output HTML file path')
    
    args = parser.parse_args()
    
    generator = GanttGenerator(args.tasks)
    
    try:
        project_data = generator.load_tasks()
        generator.generate_svg(project_data, args.output_svg)
        generator.generate_html_preview(project_data, args.output_html)
        print("✅ Gantt chart generation completed successfully!")
    except Exception as e:
        print(f"❌ Error generating Gantt chart: {e}")
        return 1
    
    return 0

if __name__ == "__main__":
    exit(main())
