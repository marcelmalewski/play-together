import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";

export const apiSlice = createApi({
  reducerPath: "api",
  baseQuery: fetchBaseQuery({
    baseUrl: "/api",
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
    logout: builder.mutation<void, void>({
      query: () => ({
        url: "/logout",
        method: "POST",
      }),
    }),
    getMyData: builder.query<any, void>({
      query: () => "/persons/@me",
      providesTags: ["MyData"],
    }),
  }),
});

export const { useLoginMutation, useLogoutMutation, useGetMyDataQuery } =
  apiSlice;
