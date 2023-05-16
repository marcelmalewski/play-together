import { ReactNode } from "react";

type FullScreenCardProps = {
    children: ReactNode;
};

export function FullScreenCard({ children }: FullScreenCardProps) {
    return (
        <div className="flex min-h-screen items-center justify-center bg-base-background">
            <div className="w-full max-w-md">{children}</div>
        </div>
    );
}

FullScreenCard.Body = function ({ children }: FullScreenCardProps) {
    return (
        <div className="rounded-lg bg-white p-6 shadow-card">{children}</div>
    );
};

FullScreenCard.BelowCard = function ({ children }: FullScreenCardProps) {
    return <div className="mt-2 flex justify-center gap-3 ">{children}</div>;
};
