import React from "react";
import { ErrorMessage, Field } from "formik";
import { FormikTextError } from "./FormikTextError";
import { BasicFormProps } from "../../interfaces/formikInterfaces";

export function FormikInput({ label, name, ...rest }: BasicFormProps) {
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
