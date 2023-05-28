import React from "react";

export function FormikTextError(props: any) {
  console.log(props);
  return <div className="text-lg text-red-500">{props.children}</div>;
}
