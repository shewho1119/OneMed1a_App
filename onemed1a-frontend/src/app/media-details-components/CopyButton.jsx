// src/components/CopyButton.jsx
'use client';


/**
 * CopyButton Component
 *
 * Copies the provided text to the user's clipboard when clicked.
 *
 * @param {Object} props
 * @param {string} props.text - The text string to copy to the clipboard.
 * @returns {JSX.Element} A small "Copy" button.
 */
export default function CopyButton({ text }) {
  async function onCopy() {
    try { await navigator.clipboard.writeText(text || ''); } catch {}
  }
  return (
    <button onClick={onCopy} type="button" className="text-xs text-slate-600 hover:text-black">
      Copy
    </button>
  );
}
