import ProfileContent from '../components/ProfileContent';
import Aside from '../components/layout/Aside';

const BASE_URL = 'http://localhost:5173';

function UserProfile() {
  return (
    <>
      <Aside
        title={'마이페이지'}
        categories={{
          titles: ['내 프로필', '내 활동'],
          path: [`${BASE_URL}/user/profile`, `${BASE_URL}/user/activities`],
        }}
      />
      <ProfileContent />
    </>
  );
}

export default UserProfile;
