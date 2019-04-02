import chai from 'chai';
import { ipv4ValidationFunction } from '../services/ipv4validation'

const expect = chai.expect;

describe('ipv4ValidationFunction()', function () {
  it('should return a true', function () {

    expect(ipv4ValidationFunction("127.0.0.1")).to.be.true;
    expect(ipv4ValidationFunction("0.0.0.0")).to.be.true;
    expect(ipv4ValidationFunction("192.168.0.1")).to.be.true;
    expect(ipv4ValidationFunction("255.255.255.255")).to.be.true;
  });

  it('should return a false', function () {
    expect(ipv4ValidationFunction("192.168.1.2.3")).to.be.false;
    expect(ipv4ValidationFunction("a.b.c.d")).to.be.false;
    expect(ipv4ValidationFunction("255.256.250.0")).to.be.false;
    expect(ipv4ValidationFunction("....")).to.be.false;
  });
});
