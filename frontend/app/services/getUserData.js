const getUserData = () => {
  const stringifiedUserDataItem = localStorage.getItem("userData")
  const stringifiedUserData = stringifiedUserDataItem !== null ? stringifiedUserDataItem : ''
  return JSON.parse(stringifiedUserData)
};

export default getUserData;