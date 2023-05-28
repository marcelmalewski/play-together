import { ReactNode } from "react";

interface FullScreenCardProps {
  children: ReactNode;
}

export function FullScreenForm({ children }: FullScreenCardProps) {
  return (
    <div className="flex min-h-screen items-center justify-center">
      <div>Welcome page</div>
      <div className="w-full max-w-md">{children}</div>
    </div>
  );
}

FullScreenForm.Body = function ({ children }: FullScreenCardProps) {
  return <div className="rounded-lg bg-slate-800 p-6">{children}</div>;
};

FullScreenForm.BelowCard = function ({ children }: FullScreenCardProps) {
  return <div className="mt-2 flex justify-center gap-3">{children}</div>;
};
