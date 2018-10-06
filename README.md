# rest-service-celsius-fahrenheit


## Passo 0: Crie, teste e depois publique o seu projeto no GitHub.
Este projeto https://github.com/bruno1022/rest-service-celsius-fahrenheit.git


## Passo 1 - Conteinerizar a aplicação com o Docker 
Arquivo Dockerfile


## Passo 2 - Operar a aplicação com balanceamento de carga em 3 instâncias distintas
```
# contruir imagem docker
docker build -t celsiusToFahrenheit .

# instânciar nginx-proxy
docker run -d -p 80:80 -e DEFAULT_HOST=proxy.example -v /var/run/docker.sock:/tmp/docker.sock:ro --name nginx jwilder/nginx-proxy

# instânciar processo 1
docker run -d -p 8080 -e VIRTUAL_HOST=proxy.example celsiusToFahrenheit

# instânciar processo 2
docker run -d -p 8080 -e VIRTUAL_HOST=proxy.example celsiusToFahrenheit

# instânciar processo 3
docker run -d -p 8080 -e VIRTUAL_HOST=proxy.example celsiusToFahrenheit

# teste da aplicação
http://localhost:8080/celsiusToFahrenheit?celsius=32
```

## Passo 3 - Operar a aplicação publicada no Azure como um conteiner Docker
```
# criar uma conta no docker hub: https://hub.docker.com (já criado, não é necessário executar)

# contruir imagem docker (já criado, não é necessário executar)
docker build -t cel2fah .

# logar no docker hub (já criado, não é necessário executar)
docker login

# versionar imagem docker (já criado, não é necessário executar)
docker tag cel2fah pucarquiteranuvem/cel2fah

# enviar imagem docker (já criado, não é necessário executar)
docker push pucarquiteranuvem/cel2fah

# acessar: https://www.katacoda.com/courses/azure/deploying-container-instances

# efetuar login na azure
az login -u $username -p $password

# executar o comando:
az container create \
-g $resource \
--name celsiusToFahrenheit \
--image pucarquiteranuvem/celsiusToFahrenheit \
--ip-address public

# teste da aplicação
http://<IP_GERADO>/celsiusToFahrenheit?celsius=32

```


## Passo 4 - Operar a aplicação como um serviço Docker Swarm
```
# acessar : https://www.katacoda.com/courses/docker-orchestration/playground
# executar os passos:
# Initialize
# Join
# Create Overlay Metwork

# executar comando:
docker service create --name celsiusToFahrenheit --network skynet --replicas 2 -p 80:80 pucarquiteranuvem/celsiusToFahrenheit


# executar comando abaixo nos dois servidores pra conferir as instâncias que estão rodando
docker ps

# clicar no símbolo de + e criar uma nova aba do web preview na porta 80
# neste momento ficou:
https://2886795267-8443-ollie01.environments.katacoda.com/celsiusToFahrenheit?celsius=32
```


## Passo 5 - Resumo

Defina os conceitos abaixo

### O que é o Docker?

É  uma tecnologia de containerização que permite a criação e o uso de containers Linux.

Com o DOCKER, é possível lidar com os containers como se fossem máquinas virtuais modulares e extremamente leves. Além disso, os containers oferecem maior flexibilidade. Com eles, é possível criar, implantar, copiar e migrá-los de um ambiente para outro.

O Docker possibilita o encapsulamento de todo um ambiente de desenvolvimento, isso diminui drasticamente o tempo de deploy da sua aplicação do ambiente de desenvolvimento para produção. Como o ambiente é sempre o mesmo, você não precisa fazer ajustes, essa é a grande maravilha do Docker.

### O que é uma imagem Docker?

Images Docker são compostas por sistemas de arquivos de camadas que ficam uma sobre as outras. Ela é a nossa base para construção de uma aplicação, ela pode ser desde o base do CentOS como também um CentOS com Apache, PHP e MySQL.

Em uma Image temos o que chamamos de base que é um sistema de arquivos de inicialização bootfs, que é muito parecido com o sistema de boot do Linux / Unix. Nessa parte é onde temos toda a criação dos cgroups para fazer todo o controle e limitações de processos, namespace para blindar o container. O Docker nunca irá interagir com o sistema de arquivos de inicialização, pois quando o recipiente é iniciado ele é movido para a memória e o boot do sistema de arquivos é desmontado para liberar a memória RAM usada pela imagem de disco initrd. Isso parece muito com a pilha de virtualização típica do Linux. A próxima camada de uma imagem é a raiz na qual trabalha o rootfs, na parte superior do sistema de arquivos de inicialização, este rootfs pode ser um ou mais sistemas operacionais (Ex: Ubuntu, CentOS).

No momento do boot o sistema de arquivos raiz é montado somente leitura e depois de uma checagem de integridade é alterado para leitura e gravação. Isso no começo pode ser muito confuso, mas com a imagem abaixo vocês devem entender melhor como funciona.

### O que é um conteiner Docker?

Containers são os aplicativos prontos criados a partir do Docker Images ou você pode dizer que um Docker Container é uma instância em execução de uma Docker Image e contém todo o pacote necessário para executar o aplicativo. Este é o melhor utilitário do Docker.

### O que é um serviço Docker?

Um serviço é um grupo de contêineres da mesma imagem: tag. Os serviços simplificam o dimensionamento de seu aplicativo. Com o Docker Cloud, basta arrastar um controle deslizante para alterar o número de contêineres em um serviço. Antes de poder implantar um serviço no Docker Cloud, você deve ter pelo menos um nó implantado.

### O que é um enxame Docker (Swarm)?

O Docker Swarm é uma ferramenta de clustering e agendamento para contêineres do Docker. Com o Swarm, os administradores e desenvolvedores de TI podem estabelecer e gerenciar um cluster de nós do Docker como um único sistema virtual.

### O que é uma pilha de serviços Docker (Stack)

O Docker Stack pode ser usado para gerenciar um aplicativo de vários serviços. Ele também move muitas das opções que você inseriria no serviço de encaixe no arquivo .yml (como docker-cloud.yml ou docker-compose.yml) para facilitar a reutilização. Ele funciona como um "script" de front end na parte superior do gerenciador de enxame docker usado pelo cluster do DOcker Swarm, para que você possa fazer tudo que a docker stack faz com o serviço do Docker.

