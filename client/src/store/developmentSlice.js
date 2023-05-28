/* eslint-disable no-param-reassign */
import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';

import {
  fetchPopularDevelopments,
  fetchRealTimeDevelopments,
  fetchAllDevelopments,
} from '../api/development';

const initialState = {
  status: 'loading',
  popularRanking: {
    status: 'loading',
    data: [],
  },
  realTimeRanking: {
    status: 'loading',
    data: [],
  },
  allDevelopments: {
    status: 'loading',
    data: [],
  },
  error: null,
};

export const fetchPopularDevelopmentsAction = createAsyncThunk(
  'fetchPopularDevelopments',
  async (_, rejectWithValue) => fetchPopularDevelopments(_, rejectWithValue),
);

export const fetchRealTimeDevelopmentsAction = createAsyncThunk(
  'fetchRealTimeDevelopments',
  async (_, rejectWithValue) => fetchRealTimeDevelopments(_, rejectWithValue),
);

export const fetchAllDevelopmentsAction = createAsyncThunk(
  'fetchAllDevelopments',
  async (_, rejectWithValue) => fetchAllDevelopments(_, rejectWithValue),
);

const developmentsSlice = createSlice({
  name: 'developments',
  initialState,
  extraReducers: builder => {
    builder.addCase(fetchPopularDevelopmentsAction.pending, (state, action) => {
      state.popularRanking.status = 'loading';
    });
    builder.addCase(fetchPopularDevelopmentsAction.fulfilled, (state, action) => {
      state.popularRanking.status = 'success';
      state.popularRanking.data = action.payload.popularRanking;
    });
    builder.addCase(fetchPopularDevelopmentsAction.rejected, (state, action) => {
      state.popularRanking.status = 'failed';
    });
    builder.addCase(fetchRealTimeDevelopmentsAction.pending, (state, action) => {
      state.realTimeRanking.status = 'loading';
    });
    builder.addCase(fetchRealTimeDevelopmentsAction.fulfilled, (state, action) => {
      state.realTimeRanking.status = 'success';
      state.realTimeRanking.data = action.payload.realTimeRanking;
    });
    builder.addCase(fetchRealTimeDevelopmentsAction.rejected, (state, action) => {
      state.realTimeRanking.status = 'failed';
    });
    builder.addCase(fetchAllDevelopmentsAction.pending, (state, action) => {
      state.allDevelopments.status = 'loading';
    });
    builder.addCase(fetchAllDevelopmentsAction.fulfilled, (state, action) => {
      state.allDevelopments.status = 'success';
      state.allDevelopments.data = action.payload.allDevelopments;
    });
    builder.addCase(fetchAllDevelopmentsAction.rejected, (state, action) => {
      state.allDevelopments.status = 'failed';
    });
  },
});

export default developmentsSlice;
