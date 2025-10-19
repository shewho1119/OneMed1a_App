import LoadingSpinner from '../components/ui/LoadingSpinner';

/**
 * Route-level loading component.
 *
 * This component is automatically rendered by Next.js
 * when a route segment is in a pending (loading) state.
 * It uses the shared `LoadingSpinner` UI component for consistency.
 *
 * @returns {JSX.Element} A spinner indicating that the page is loading.
 */
export default function Loading() {
  return <LoadingSpinner />;
}