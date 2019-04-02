import { cidrToMaskFunction } from '../services/cidrtomask';


export const cidrToMask = (req, res, next) => {
  let value = req.query.value ? req.query.value : false;
  if (!value) {
    res.send(422, 'No value provided' )
  } else {
    let response = {
      "function": "cidrToMask",
      "input" : value,
      "output": cidrToMaskFunction(value)
    };
    res.send(response);
    next();
  }

}
