import { Form, Formik } from "formik";
import * as Yup from "yup";
import { LoginFormValues } from "../../interfaces/authInterfaces";
import { FormikInput } from "../../components/formik/FormikInput";

export function LoginPage() {
  const initialValues: LoginFormValues = {
    loginOrEmail: "",
    password: "",
  };
  const loginSchema = Yup.object().shape({
    loginOrEmail: Yup.string().required("Login/email is required"),
    password: Yup.string().required("Password is required"),
  });

  function tryToLogin() {
    console.log("logged in");
  }

  return (
    <Formik
      initialValues={initialValues}
      validationSchema={loginSchema}
      onSubmit={tryToLogin}
    >
      {(formik) => (
        <Form className=" p-10">
          <div className="flex flex-row">
            <FormikInput
              className="block w-full rounded-lg border border-slate-600 bg-slate-800 p-2.5 text-lg placeholder-gray-400 focus:outline-none"
              label="Name"
              name="name"
            />
          </div>
          {/*<div className="flex w-full items-center justify-center">*/}
          {/*  <button*/}
          {/*    type="submit"*/}
          {/*    className="bg-o-4 enabled:hover:bg-o-4-20d mt-8 h-12 w-40 rounded-md text-2xl disabled:cursor-not-allowed disabled:opacity-40"*/}
          {/*    disabled={formik.isSubmitting || formik.isValidating}*/}
          {/*  >*/}
          {/*    create*/}
          {/*  </button>*/}
          {/*</div>*/}
        </Form>
      )}
    </Formik>
  );
}
