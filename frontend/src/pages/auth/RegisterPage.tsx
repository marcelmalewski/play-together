import {
  ErrorMessage,
  Form,
  Formik,
  FormikHelpers,
  FormikValues,
} from "formik";
import * as Yup from "yup";
import { FormikInput } from "../../components/formik/FormikInput";
import { FullScreenFormLayout } from "../../layouts/FullScreenFormLayout";
import { FormikDatePicker } from "../../components/formik/FormikDatePicker";
import React from "react";
// @ts-ignore
import DatePicker from "react-datepicker";
import { RegisterFormValues } from "../../interfaces/authInterfaces";
import { FormikTextError } from "../../components/formik/FormikTextError";

export function RegisterPage() {
  const defaultPlayingTimeStart = new Date();
  defaultPlayingTimeStart.setHours(0);
  defaultPlayingTimeStart.setMinutes(0);
  const defaultPlayingTimeEnd = new Date();
  defaultPlayingTimeEnd.setHours(23);
  defaultPlayingTimeEnd.setMinutes(59);

  const initialValues: RegisterFormValues = {
    login: "",
    password: "",
    email: "",
    birthDate: new Date(),
    playingTimeStart: defaultPlayingTimeStart,
    playingTimeEnd: defaultPlayingTimeEnd,
  };
  const registerSchema = Yup.object().shape({
    login: Yup.string().min(3).max(20).required("login is required"),
    //TODO do testow wymagania hasla obnizone z min 8
    password: Yup.string().min(3).max(20).required("password is required"),
    //TODO do zweryfikowania co dokladnie akceptuje .email() w porównaniu do api
    email: Yup.string().email().required("email is required"),
    birthDate: Yup.date()
      .typeError("birth date is required")
      .required("birth date is required")
      .max(new Date(), "birth date can't be in future"),
    playingTimeStart: Yup.date()
      .typeError("playing time is required")
      .required("playing time is required"),
    playingTimeEnd: Yup.date()
      .typeError("playing time is required")
      .required("playing time is required")
      .test("end is after start", "must be after", (value, ctx) => {
        return value > ctx.parent.playingTimeStart;
      }),
  });

  function tryToRegister(
    values: FormikValues,
    formikHelpers: FormikHelpers<RegisterFormValues>
  ) {
    //TODO z time pobrać tylko czas i jako string wyslac
    console.log(values);
    console.log("registerd in");
    //dodac field error dla playingTime i robic tutaj reczne walidowanie i ez
    formikHelpers.setFieldError("birthDate", "yes");
    formikHelpers.setSubmitting(false);

    // zmienic daty na odpowiedni format
  }

  return (
    <FullScreenFormLayout>
      <Formik
        initialValues={initialValues}
        validationSchema={registerSchema}
        onSubmit={tryToRegister}
      >
        {(formik) => (
          <Form className="flex w-2/5 flex-col items-center justify-between rounded-lg bg-form-bg p-12">
            <div className="flex flex-col gap-4">
              <FormikInput
                className="block w-full rounded-lg border border-form-border bg-form-bg p-2 placeholder-placeholder focus:outline-none"
                label="Login"
                name="login"
              />
              <FormikInput
                className="block w-full rounded-lg border border-form-border bg-form-bg p-2 placeholder-placeholder focus:outline-none"
                label="Password"
                name="password"
                type="password"
              />
              <FormikInput
                className="block w-full rounded-lg border border-form-border bg-form-bg p-2 placeholder-placeholder focus:outline-none"
                label="Email"
                name="email"
              />
              <FormikDatePicker
                label="Birth date"
                name="birthDate"
                className="block w-full rounded-lg border border-form-border bg-form-bg p-2 placeholder-placeholder focus:outline-none"
              />
              <div>
                <p>Playing time:</p>
                <div className="flex flex-row items-center gap-2">
                  <DatePicker
                    id="playingTimeStart"
                    className="block w-full rounded-lg border border-form-border bg-form-bg p-2 placeholder-placeholder focus:outline-none"
                    onChange={(newPlayingStartTime: any) => {
                      if (newPlayingStartTime < formik.values.playingTimeEnd) {
                        formik.setFieldValue(
                          "playingTimeStart",
                          newPlayingStartTime
                        );
                      }
                      formik.setFieldValue(
                        "playingTimeStart",
                        newPlayingStartTime
                      );
                    }}
                    selected={formik.values.playingTimeStart}
                    minTime={defaultPlayingTimeStart}
                    maxTime={formik.values.playingTimeEnd}
                    excludeTimes={[formik.values.playingTimeEnd]}
                    showTimeSelect
                    showTimeSelectOnly
                    timeIntervals={60}
                    timeCaption="Time"
                    timeFormat="HH:mm"
                    dateFormat="HH:mm"
                  />
                  <p>-</p>
                  <DatePicker
                    id="playingTimeEnd"
                    className="block w-full rounded-lg border border-form-border bg-form-bg p-2 placeholder-placeholder focus:outline-none"
                    onChange={(newPlayingEndTime: Date) => {
                      if (newPlayingEndTime > formik.values.playingTimeStart) {
                        formik.setFieldValue(
                          "playingTimeEnd",
                          newPlayingEndTime
                        );
                      }
                      formik.setFieldValue("playingTimeEnd", newPlayingEndTime);
                    }}
                    selected={formik.values.playingTimeEnd}
                    minTime={formik.values.playingTimeStart}
                    maxTime={defaultPlayingTimeEnd}
                    excludeTimes={[formik.values.playingTimeStart]}
                    showTimeSelect
                    showTimeSelectOnly
                    timeIntervals={60}
                    timeCaption="Time"
                    timeFormat="HH:mm"
                    dateFormat="HH:mm"
                  />
                </div>
                <ErrorMessage
                  name={"playingTimeEnd"}
                  component={FormikTextError}
                />
              </div>
            </div>
            <button
              type="submit"
              className="mt-8 h-12 w-40 rounded-md bg-base-button text-2xl duration-200 enabled:hover:bg-base-button-hov disabled:cursor-not-allowed disabled:opacity-40"
              disabled={formik.isSubmitting || formik.isValidating}
            >
              Create
            </button>
          </Form>
        )}
      </Formik>
    </FullScreenFormLayout>
  );
}
