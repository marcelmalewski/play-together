import {Outlet} from "react-router-dom";
import {FullScreenCard} from "../../components/FullScreenCard";

export function AuthLayout() {
   return (
      <FullScreenCard>
         <FullScreenCard.Body>
            <Outlet />
         </FullScreenCard.Body>
         <FullScreenCard.BelowCard>
            <p className="text-center text-gray-600 dark:text-gray-400">
               &copy; 2021. All rights reserved.
            </p>
         </FullScreenCard.BelowCard>
      </FullScreenCard>
   )
}
