function ajax(url, requestMethod, requestBody){
  const fetchData = {
      headers: {
          "Content-Type": "application/json"
      },
      method: requestMethod
  }

  if(requestBody){
      fetchData.body = JSON.stringify(requestBody)
  }

  return fetch(url, fetchData).then((response) => {
    if (response.status === 200) {
      const contentType = response.headers.get("content-type");
      if (contentType && contentType.indexOf("application/json") !== -1) {
        return response.json();
      } else {
        return response.text();
      }
    }
  });
}
export default ajax;
