import React from "react";
import { Field, ErrorMessage } from "formik";
import { FormikTextError } from "./FormikTextError";

export function FormikInput(props: any) {
  const { label, name, ...rest } = props;

  return (
    <div>
      <label htmlFor={name}>{label}</label>
      <Field
        id={name}
        placeholder={label + "..."}
        name={name}
        {...rest}
      ></Field>
      <ErrorMessage name={name} component={FormikTextError} />
    </div>
  );
}
