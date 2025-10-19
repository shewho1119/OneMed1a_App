import Image from "next/image";

/**
 * BackgroundImage Component
 *
 * Renders a responsive background image with a dark gradient overlay.
 * If no `src` is provided, displays a neutral-colored fallback background.
 *
 * @param {Object} props
 * @param {string} props.src - The image URL to render as the background.
 * @param {string} [props.alt=""] - Descriptive alt text for accessibility.
 * @returns {JSX.Element} A responsive div with the background image and gradient overlay.
 */
export default function BackgroundImage({ src, alt = "" }) {
    return (
        <div className="relative h-64 w-full overflow-hidden md:h-80">
            {src ? (
                <Image
                    src={src}
                    alt={alt}
                    fill
                    priority
                    className="object-cover opacity-70"
                    sizes="100vw"
                />
            ) : (
                <div className="h-full w-full bg-neutral-800" />
            )}
            <div className="absolute inset-0 bg-gradient-to-b from-neutral-900/10 to-neutral-950" />
        </div>
    );
}
