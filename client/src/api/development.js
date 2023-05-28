// TODO: develop VS Prod mode에 따른 프로젝트 환경 셋팅

import axios from '../utils/axios';

const popularRankingEndpoint = 'posts/popular-ranking';
const realTimeRankingEndpoint = 'posts/realtime-ranking';
const allDevelopmentEndpoint = 'posts';
// const popularRankingEndpoint = 'popularRanking';
// const realTimeRankingEndpoint = 'realTimeRanking';
// const allDevelopmentEndpoint = 'allDevelopments';

export const fetchPopularDevelopments = async (something, rejectWithValue) => {
  try {
    const response = await axios.get(popularRankingEndpoint);
    if (response.status >= 200 && response.status < 300) {
      return { popularRanking: response.data.data };
    }
  } catch (err) {
    return rejectWithValue({ error: err.message }); // rejectWithValue적용
  }

  return null;
};

export const fetchRealTimeDevelopments = async (something, rejectWithValue) => {
  // console.log(`axios.get.baseurl`, axios.defaults.baseURL);
  try {
    const response = await axios.get(`${realTimeRankingEndpoint}`);

    if (response.status >= 200 && response.status < 300) {
      return { realTimeRanking: response.data.data };
    }
  } catch (err) {
    return rejectWithValue({ error: err.message }); // rejectWithValue적용
  }

  return null;
};

export const fetchAllDevelopments = async (_, rejectWithValue) => {
  try {
    const response = await axios.get(allDevelopmentEndpoint);
    if (response.status >= 200 && response.status < 300) {
      return { allDevelopments: response.data.data };
    }
  } catch (err) {
    return rejectWithValue({ error: err.message }); // rejectWithValue적용
  }

  return null;
};

export default {};
