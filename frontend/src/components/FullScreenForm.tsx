import { ReactNode } from "react";

interface FullScreenCardProps {
  children: ReactNode;
}

export function FullScreenForm({ children }: FullScreenCardProps) {
  return (
    <div className="flex min-h-screen items-center justify-center">
      <div className="w-full max-w-md">{children}</div>
    </div>
  );
}

FullScreenForm.Body = function ({ children }: FullScreenCardProps) {
  return <div className="rounded-lg bg-o-2 p-6">{children}</div>;
};

FullScreenForm.BelowCard = function ({ children }: FullScreenCardProps) {
  return <div className="mt-2 flex justify-center gap-3">{children}</div>;
};
