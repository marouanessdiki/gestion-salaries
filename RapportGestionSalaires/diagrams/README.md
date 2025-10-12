Diagrams as Code (Mermaid)

This folder contains the project diagrams written in Mermaid format so you can paste them directly into eraser.io (supports Mermaid) or any Mermaid renderer.

Files
- use-case.mmd — Use-case style overview (actors and system use cases)
- sequence-attestation.mmd — Sequence for attestation generation
- sequence-employees.mmd — Sequence for employee CRUD flows
- class-model.mmd — Class diagram (entities and conceptual links)
- workflow-attestation.mmd — Attestation generation flow
- architecture-backend.mmd — Backend layered architecture
- architecture-frontend.mmd — Frontend component/service architecture
- waterfall.mmd — Process flow (waterfall)

How to render
- Eraser: create a diagram, choose Mermaid, paste the file contents.
- VS Code: install “Markdown Preview Mermaid Support”, open a Markdown file with a ```mermaid block.
- Mermaid CLI: `mmdc -i input.mmd -o output.svg` (requires Node + @mermaid-js/mermaid-cli).

Notes
- Class and sequence content is grounded in the codebase (Spring Boot controllers/services/repos, React components, and entities).
- The DB relationships here are conceptual; in code `Attestation` stores `employeId` rather than a JPA relation.

