import { Form, Formik, FormikHelpers, FormikValues } from "formik";
import * as Yup from "yup";
import { FormikInput } from "../../components/formik/FormikInput";
import { FullScreenFormLayout } from "../../layouts/FullScreenFormLayout";
import { FormikDatePicker } from "../../components/formik/FormikDatePicker";
import React from "react";
// @ts-ignore
import DatePicker from "react-datepicker";
import { RegisterFormValues } from "../../interfaces/authInterfaces";

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
    login: Yup.string().required("Login is required"),
    password: Yup.string().required("Password is required"),
    email: Yup.string().required("Email is required"),
    birthDate: Yup.date()
      .typeError("Birth date is required")
      .required("Birth date is required")
      .max(new Date(), "Birth date can't be in future"),
    playingTimeStart: Yup.date()
      .typeError("Playing time is required")
      .required("Playing time is required"),
    playingTimeEnd: Yup.date()
      .typeError("Playing time is required")
      .required("Playing time is required"),
  });

  function tryToRegister(
    values: FormikValues,
    formikHelpers: FormikHelpers<RegisterFormValues>
  ) {
    //TODO z time pobrać tylko czas i jako string wyslac
    console.log(values);
    console.log("registerd in");
    formikHelpers.setSubmitting(false);
  }

  //TODO ograniczenie wiekowe i startowa data inna? od birthdate
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
                    onChange={(val: any) =>
                      formik.setFieldValue("playingTimeStart", val)
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
                    onChange={(val: any) =>
                      formik.setFieldValue("playingTimeEnd", val)
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
