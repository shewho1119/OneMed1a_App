'use client';
import Image from 'next/image';


/**
 * LoadingSpinner Component
 *
 * Displays a loading indicator with a pulsing logo and optional overlay.
 * Can be used globally (full-screen overlay) or within a specific container.
 *
 * @param {Object} props
 * @param {string} [props.message='Loading...'] - The text message shown below the spinner.
 * @param {boolean} [props.overlay=true] - Whether to display a translucent overlay behind the spinner.
 * @param {'viewport' | 'container'} [props.scope='viewport'] - The area the overlay covers:
 *    - `'viewport'`: fixed to the full screen
 *    - `'container'`: positioned absolutely inside a parent container
 *
 * @returns {JSX.Element} A visually centered loading spinner component.
 */
export default function LoadingSpinner({
  message = 'Loading...',
  overlay = true,
  scope = 'viewport', // 'viewport' | 'container'
}) {
  // Inner content: logo + message
  const inner = (
    <div className="flex flex-col items-center justify-center">
      {/* Animated logo */}
      <div className="animate-pump">
        <Image
          src="/Favicon.png"
          alt="Loading logo"
          width={100}
          height={100}
          className="drop-shadow-[0_0_20px_rgba(255,255,255,0.9)]"
        />
      </div>

      {/* Loading text */}
      <p className="mt-4 text-lg font-semibold text-white drop-shadow-[0_0_8px_rgba(255,255,255,0.9)] animate-pulse">
        {message}
      </p>
    </div>
  );

  // If overlay is disabled, just return the spinner itself
  if (!overlay) return inner;

  // Determine whether overlay covers viewport or container
  const overlayClass =
    scope === 'container' ? 'absolute inset-0' : 'fixed inset-0';

  return (
    <div
      className={`${overlayClass} z-50 grid place-items-center bg-black/70`}
    >
      {inner}
    </div>
  );
}
