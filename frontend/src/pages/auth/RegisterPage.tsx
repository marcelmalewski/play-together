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
import { format } from "date-fns";
// @ts-ignore
import DatePicker from "react-datepicker";
import {
  RegisterBody,
  RegisterFormValues,
} from "../../interfaces/authInterfaces";
import { FormikTextError } from "../../components/formik/FormikTextError";
import { DATE_FORMAT, MIN_AGE, TIME_FORMAT } from "../../other/constants";

export function RegisterPage() {
  const defaultPlayingTimeStart = new Date();
  defaultPlayingTimeStart.setHours(0);
  defaultPlayingTimeStart.setMinutes(0);
  const defaultPlayingTimeEnd = new Date();
  defaultPlayingTimeEnd.setHours(23);
  defaultPlayingTimeEnd.setMinutes(59);
  const today = new Date();
  const minBirthDate = subtractYears(today, MIN_AGE);

  const initialValues: RegisterFormValues = {
    login: "",
    password: "",
    email: "",
    birthdate: minBirthDate,
    playingTimeStart: defaultPlayingTimeStart,
    playingTimeEnd: defaultPlayingTimeEnd,
  };

  const registerSchema = Yup.object().shape({
    login: Yup.string().min(3).max(20).required("login is required"),
    password: Yup.string().min(8).max(20).required("password is required"),
    //TODO do zweryfikowania co dokladnie akceptuje .email() w porównaniu do api
    email: Yup.string().email().required("email is required"),
    birthdate: Yup.date()
      .typeError("birthdate is required")
      .required("birthdate is required")
      .max(minBirthDate, "minimum age is 15 years"),
    playingTimeStart: Yup.date()
      .typeError("playing time is required")
      .required("playing time is required"),
    playingTimeEnd: Yup.date()
      .typeError("playing time is required")
      .required("playing time is required")
      .test(
        "end time is after start time",
        "end time must be after start time",
        (value, ctx) => {
          return value > ctx.parent.playingTimeStart;
        }
      ),
  });

  function subtractYears(date: Date, years: number) {
    const result = new Date(date);
    result.setFullYear(date.getFullYear() - years);
    return result;
  }

  function tryToRegister(
    values: FormikValues,
    formikHelpers: FormikHelpers<RegisterFormValues>
  ) {
    formikHelpers.setSubmitting(false);
    const registerFormValues = values as RegisterFormValues;

    // zmienic daty na odpowiedni format date-fns
    const registerBody: RegisterBody = {
      login: registerFormValues.login,
      password: registerFormValues.password,
      email: registerFormValues.email,
      birthdate: format(registerFormValues.birthdate, DATE_FORMAT),
      playingTimeStart: format(
        registerFormValues.playingTimeStart,
        TIME_FORMAT
      ),
      playingTimeEnd: format(registerFormValues.playingTimeEnd, TIME_FORMAT),
    };
    console.log(registerBody);
    //TODO udana rejestracj to przeniesienie do logowania
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
                name="birthdate"
                className="block w-full rounded-lg border border-form-border bg-form-bg p-2 placeholder-placeholder focus:outline-none"
              />
              <div>
                <p>Playing time:</p>
                <div className="flex flex-row items-center gap-2">
                  <DatePicker
                    id="playingTimeStart"
                    className="block w-full rounded-lg border border-form-border bg-form-bg p-2 placeholder-placeholder focus:outline-none"
                    onChange={(newPlayingStartTime: any) =>
                      formik.setFieldValue(
                        "playingTimeStart",
                        newPlayingStartTime
                      )
                    }
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
                    onChange={(newPlayingEndTime: Date) =>
                      formik.setFieldValue("playingTimeEnd", newPlayingEndTime)
                    }
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
