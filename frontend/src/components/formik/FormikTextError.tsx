import React from "react";

//TODO czy da sie poprawić any
export function FormikTextError(props: any) {
  return <div className="text-lg text-error">{props.children}</div>;
}
