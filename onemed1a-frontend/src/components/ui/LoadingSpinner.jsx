// 'use client';
// import React from 'react';

// export default function LoadingSpinner() {
//   return (
//     <div className="fixed inset-0 flex items-center justify-center bg-black/30 backdrop-blur-sm z-50">
//       <div className="w-12 h-12 border-4 border-white border-t-transparent rounded-full animate-spin"></div>
//     </div>
//   );
// }

// 'use client';
// import Image from 'next/image';

// export default function LoadingSpinner() {
//   return (
//     <div className="flex flex-col items-center justify-center">
//       {/* Pumping logo animation */}
//       <div className="animate-pump">
//         <Image
//           src="/Favicon.png"      
//           alt="Loading logo"
//           width={100}
//           height={100}
//           className="drop-shadow-lg"
//         />
//       </div>

//       {/* Loading text */}
//       <p className="mt-4 text-lg font-medium text-white animate-pulse">
//         Loading...
//       </p>
//     </div>
//   );
// }


// 'use client';
// import Image from 'next/image';

// export default function LoadingSpinner({
//   message = 'Loading...',
//   overlay = true,
// }) {
//   const inner = (
//     <div className="flex flex-col items-center justify-center">
//       <div className="animate-pump">
//         <Image
//           src="/Favicon.png"
//           alt="Loading logo"
//           width={100}
//           height={100}
//           className="drop-shadow-lg"
//         />
//       </div>
//       <p className="mt-4 text-lg font-medium text-white">{message}</p>
//     </div>
//   );

//   if (!overlay) return inner;

//   return (
//     <div className="fixed inset-0 z-50 grid place-items-center bg-black/50 backdrop-blur-sm">
//       {inner}
//     </div>
//   );
// }


'use client';
import Image from 'next/image';

export default function LoadingSpinner({
  message = 'Loading...',
  overlay = true,
  scope = 'viewport', // 'viewport' | 'container'
}) {
  const inner = (
    <div className="flex flex-col items-center justify-center">
      {/* Pumping logo */}
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

  if (!overlay) return inner;

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
