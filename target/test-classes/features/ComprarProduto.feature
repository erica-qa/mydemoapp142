#language: pt

Funcionalidade: Comprar Produto
    Esquema do Cenario: Comprar com Sucesso
        Dado que acesso o My Demo App
        E verifico o logo e nome da secao  
        E localizo o <produto> que esta por <preco>
        Quando clico na imagem do <num_produto>
        Entao na tela do produto verifico o <produto> e o <preco>
        Quando arrasto para cima e clico no botao Add Cart
        Entao na tela do carrinho verifico o <produto> <preco> e <quantidade>
