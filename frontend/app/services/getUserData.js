const getLoginData = () => {
  const stringifiedLoginDataItem = localStorage.getItem("loginData")
  const stringifiedLoginData = stringifiedLoginDataItem !== null ? stringifiedLoginDataItem : ''
  return JSON.parse(stringifiedLoginData)
};

export default getLoginData;