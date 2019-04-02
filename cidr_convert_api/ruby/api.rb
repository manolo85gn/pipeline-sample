require 'sinatra/base'
require 'json'
require_relative 'convert'

# aa
class App < Sinatra::Base
  set :port, 8000
  set :bind, '0.0.0.0'

  get '/' do
    'OK'
  end

  get '/_health' do
    'OK'
  end

  get '/cidr-to-mask' do
    convert = CidrMaskConvert.new
    value = params[:value]
    content_type :json
    {
      function: 'cidrToMask',
      input: value,
      output: convert.cidr_to_mask(value)
    }.to_json
  end

  get '/mask-to-cidr' do
    convert = CidrMaskConvert.new
    value = params[:value]
    content_type :json
    {
      function: 'maskToCidr',
      input: value,
      output: convert.mask_to_cidr(value)
    }.to_json
  end

  get 'ip-validation' do
    validate = IpValidate.new
    value = params[:value]
    content_type :json
    {
      function: 'ipv4Validation',
      input: value,
      output: validate.ipv4_validation(value)
    }.to_json
  end
end

App.run!
