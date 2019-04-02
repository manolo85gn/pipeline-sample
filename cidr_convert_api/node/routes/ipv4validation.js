import { ipv4ValidationFunction } from '../services/ipv4validation';


export const ipv4Validation = (req, res, next) => {
  let value = req.query.value ? req.query.value : false;
  if (!value) {
    res.send(422, 'No value provided' )
  } else {
    let response = {
      "function": "ipv4Validation",
      "input" : value,
      "output": ipv4ValidationFunction(value)
    };
    res.send(response);
    next();
  }

}
