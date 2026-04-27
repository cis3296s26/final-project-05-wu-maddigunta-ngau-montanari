{
  description = "irlquestbook dev shell";

  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixpkgs-unstable";
    flake-utils.url = "github:numtide/flake-utils";
  };

  outputs =
    {
      self,
      nixpkgs,
      flake-utils,
    }:
    flake-utils.lib.eachDefaultSystem (
      system:
      let
        pkgs = import nixpkgs { inherit system; };
        jdkWithFX = pkgs.openjdk21.override {
          enableJavaFX = true;
        };
        mavenWithFX = pkgs.maven.override {
          jdk_headless = jdkWithFX;
        };
      in
      {
        devShells.default = pkgs.mkShell {
          buildInputs = [
            jdkWithFX
            mavenWithFX
            (pkgs.jdt-language-server.override { jdk = jdkWithFX; })
            pkgs.google-java-format
          ];

          shellHook = ''
            export JAVA_HOME="${jdkWithFX}"
          '';
        };
      }
    );
}
