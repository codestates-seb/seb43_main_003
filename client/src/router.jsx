import { createBrowserRouter } from 'react-router-dom';

import Body from './components/layout/Body';
import Home from './pages/Home';
import Login from './pages/Login';
import PageNotFound from './pages/PageNotFound';
import UserProfile from './pages/UserProfile';
import UserActivity from './pages/UserActivity';
import AllDevelopment from './pages/AllDevelopments';

const routerData = [
  {
    path: '/',
    element: <Home />,
    errorElement: <PageNotFound />,
    layoutInfo: {
      isHeader: true,
      isMainContentWidthScreen: true,
      isFooter: true,
    },
  },
  {
    path: '/developments',
    element: <AllDevelopment />,
    layoutInfo: {
      isHeader: true,
      isMainContentWidthScreen: false,
      isFooter: true,
    },
  },
  {
    path: '/user/login',
    element: <Login />,
    layoutInfo: {
      isHeader: true,
      isMainContentWidthScreen: true,
      isFooter: true,
    },
  },
  {
    path: '/user/profile',
    element: <UserProfile />,
    layoutInfo: {
      isHeader: true,
      isMainContentWidthScreen: false,
      isFooter: true,
    },
  },
  {
    path: '/user/activities',
    element: <UserActivity />,
    layoutInfo: {
      isHeader: true,
      isMainContentWidthScreen: false,
      isFooter: true,
    },
  },
];

const routers = createBrowserRouter(
  routerData.reduce((accRouterArr, currentRouterInfo) => {
    const parsedRouter = {
      ...currentRouterInfo,
      element: (
        <Body layoutInfo={currentRouterInfo?.layoutInfo}>
          {currentRouterInfo.element}
        </Body>
      ),
    };

    delete parsedRouter.layoutInfo;

    return [...accRouterArr, parsedRouter];
  }, []),
);

export default routers;
