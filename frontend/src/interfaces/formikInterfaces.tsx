import type { ComponentPropsWithoutRef } from "react";

export interface BasicFormProps extends ComponentPropsWithoutRef<"input"> {
  label: string;
  name: string;
}
