const handler = async (req: any, res: any) => {
  //   const isCareGiver = req.body.isCareGiver;
  const method = "POST";
  //New user input data
  const newUser = {
    name: "",
    userName: "",
    email: "",
    phoneNumber: 0,
    password: "",
    // isCareGiver: isCareGiver,
  };

  //fetch options
  const options = {
    method: method,
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(newUser),
  };

  //fetch url
  const url = "http://localhost:8081/api/auth/register";
  try {
    const response = await fetch(url, options);
    const data = await response.json();
    return res.end(JSON.stringify({ success: data }));
  } catch (err: any) {
    res.end(JSON.stringify({ error: err.message }));
  }
};
