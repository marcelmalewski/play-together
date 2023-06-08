// @ts-ignore
import DatePicker from "react-datepicker";
import "./DatePicker.css";
import { ErrorMessage, useField, useFormikContext } from "formik";
import { FormikTextError } from "./FormikTextError";
import React from "react";
import { BasicFormProps } from "../../interfaces/formikInterfaces";

export function FormikDatePicker({ label, name, ...rest }: BasicFormProps) {
  const [field] = useField(name);
  const { setFieldValue } = useFormikContext();

  return (
    <div>
      <label htmlFor={name}>{label}</label>
      <DatePicker
        id={name}
        {...field}
        {...rest}
        selected={field.value}
        onChange={(val: any) => setFieldValue(name, val)}
      />
      <ErrorMessage name={name} component={FormikTextError} />
    </div>
  );
}
