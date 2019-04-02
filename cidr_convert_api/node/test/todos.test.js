import app from '../server';
import chai from 'chai';
import request from 'supertest';

const expect = chai.expect;

describe('Todos list API Integration Tests', function() {
  describe('#GET /_health', function() {
    it('should get server health', function(done) {
      request(app).get('/_health')
        .end(function(err, res) {
          expect(res.statusCode).to.equal(200);
          expect(res.body).to.be.an('string');
          expect(res.body).to.have.string('OK');
          done();
        });
    });
  });
});
