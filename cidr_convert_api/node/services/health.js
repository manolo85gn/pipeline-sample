export const health = (req, res, next) => {
      res.send('OK');
      next();
  }
export const healthier = (req, res, next) => {
        res.send('More Health');
        next();
  }
