# Images Directory

Place your company images in this directory:

## Required Images:

### 1. `logo.png`

- **Purpose**: Company logo for the header
- **Recommended size**: 200x200 pixels or larger (will be scaled to 50x50 in PDF)
- **Format**: PNG with transparent background preferred
- **Location**: `images/logo.png`

### 2. `cachet.png`

- **Purpose**: Company stamp/cachet for the signature section
- **Recommended size**: 300x300 pixels or larger (will be scaled to 80x80 in PDF)
- **Format**: PNG with transparent background preferred
- **Location**: `images/cachet.png`

## Image Requirements:

- **Format**: PNG recommended (supports transparency)
- **Background**: Transparent background works best
- **Quality**: High resolution (will be scaled down in PDF)
- **File names**: Must be exactly `logo.png` and `cachet.png`

## Fallback:

If images are not found, the system will automatically use text-based alternatives:

- Logo: "NETCON CONSULTING" text
- Cachet: "NETCON CONSULTING" text in stamp box

## Example:

```
images/
├── logo.png      (Your company logo)
├── cachet.png    (Your company stamp)
└── README.md     (This file)
```

After adding your images, restart the backend and generate a new attestation to see them in the PDF.
