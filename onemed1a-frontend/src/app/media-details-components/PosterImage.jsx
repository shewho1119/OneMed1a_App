import Image from "next/image";


/**
 * PosterImage Component
 *
 * Displays a poster or fallback image in a styled frame.
 * If no `src` is provided, shows a neutral placeholder.
 *
 * @param {Object} props
 * @param {string} [props.src] - The URL of the poster image.
 * @param {string} [props.alt="Poster"] - The alt text for the image.
 * @param {number} [props.width=220] - The fixed width of the poster in pixels.
 * @param {number} [props.height=320] - The fixed height of the poster in pixels.
 * @returns {JSX.Element} A framed poster image or placeholder box.
 */
export default function PosterImage({ src, alt = "Poster", width = 220, height = 320 }) {
    return (
        <div
            className="relative mx-auto overflow-hidden rounded-2xl shadow-2xl shadow-black/50 ring-1 ring-black/30 md:mx-0"
            style={{ width, height }}
        >
            {src ? (
                <Image
                    src={src}
                    alt={alt}
                    fill
                    className="object-cover"
                    sizes={`${width}px`}
                />
            ) : (
                <div className="flex h-full w-full items-center justify-center bg-neutral-800 text-neutral-400">
                    No Poster
                </div>
            )}
        </div>
    );
}
