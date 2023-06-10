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
  }),
});

export const { useLoginMutation } = apiSlice;
