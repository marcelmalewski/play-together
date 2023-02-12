import {ReactNode} from "react";

type FullScreenCardProps = {
   children: ReactNode;
}

export function FullScreenCard({ children }: FullScreenCardProps) {
   return (
      <div className="flex items-center justify-center h-screen bg-base-background">
         <div className="w-full max-w-sm p-4 rounded-md shadow-card bg-white">
            {children}
         </div>
      </div>
   );
}

FullScreenCard.Body = function FullScreenCardBody({ children }: FullScreenCardProps) {
   return (
      <div className="mt-4">
         {children}
      </div>
   );
}

FullScreenCard.BelowCard = function FullScreenCardBelowCard({ children }: FullScreenCardProps) {
   return (
      <div className="mt-6">
         {children}
      </div>
   );
}
