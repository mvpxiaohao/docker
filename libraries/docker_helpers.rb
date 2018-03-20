module DockerCookbook
  module DockerHelpers
    require 'docker'
    require 'shellwords'

    ################
    # Helper methods
    ################

    def connection
      @connection ||= begin
                        opts = {}
                        opts[:read_timeout] = read_timeout if read_timeout
                        opts[:write_timeout] = write_timeout if write_timeout

                        if host =~ /^tcp:/
                          opts[:scheme] = 'https' if tls || !tls_verify.nil?
                          opts[:ssl_ca_file] = tls_ca_cert if tls_ca_cert
                          opts[:client_cert] = tls_client_cert if tls_client_cert
                          opts[:client_key] = tls_client_key if tls_client_key
                        end
                        Docker::Connection.new(host || Docker.url, opts)
                      end
    end

    def with_retries(&_block)
      tries = api_retries
      begin
        yield
      # Only catch errors that can be fixed with retries.
      rescue Docker::Error::ServerError, # 500
             Docker::Error::UnexpectedResponseError, # 400
             Docker::Error::TimeoutError,
             Docker::Error::IOError
        tries -= 1
        retry if tries > 0
        raise
      end
    end

    def call_action(_action)
      new_resource.run_action
    end

    # https://github.com/docker/docker/blob/4fcb9ac40ce33c4d6e08d5669af6be5e076e2574/registry/auth.go#L231
    def parse_registry_host(val)
      val.sub(%r{https?://}, '').split('/').first
    end
  end
end
