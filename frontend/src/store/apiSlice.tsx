import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { RegisterBody } from "../interfaces/authInterfaces";

export const apiSlice = createApi({
  reducerPath: "api",
  baseQuery: fetchBaseQuery({
    baseUrl: "/api/v1",
  }),
  tagTypes: ["MyData"],
  endpoints: (builder) => ({
    //Auth
    login: builder.mutation<void, string>({
      query: (body) => ({
        url: "/login",
        body: body,
        method: "POST",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
      }),
    }),
    register: builder.mutation<void, RegisterBody>({
      query: (body) => ({
        url: "/auth/gamers/register",
        body: body,
        method: "POST",
        //invalidatesTags: () => ["MyData"],
      }),
    }),
    logout: builder.mutation<void, void>({
      query: () => ({
        url: "/logout",
        method: "POST",
      }),
    }),

    getMyData: builder.query<any, void>({
      query: () => "/gamers/@me",
      providesTags: ["MyData"],
    }),
  }),
});

export const {
  useRegisterMutation,
  useLoginMutation,
  useLogoutMutation,
  useGetMyDataQuery,
} = apiSlice;
