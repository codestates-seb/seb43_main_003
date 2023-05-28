import { configureStore } from '@reduxjs/toolkit';

import developmentsSlice from './developmentSlice';

const store = configureStore({
  reducer: {
    developments: developmentsSlice.reducer,
  },
});

export default store;
