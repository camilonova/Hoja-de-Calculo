package edu.log;

import java.util.Stack;
import java.util.StringTokenizer;

/**
 * Esta quiza es la clase mas importante de la aplicacion ya que es la que convierte la entrada
 * en la salida, funciona para funciones matematicas basicas, como suma, resta, multiplicacion,
 * division, exponenciacion y precedencia de parentesis.<p>
 * Para hacer dicha operacion lo fundamenta de la clase es el uso de las pilas posfija y de operadores.
 * El uso de dichas pilas garantiza la resolucion de la expresion matematica lo mas optimamente posible
 * ademas de lo mas facil para entender.<p>
 * 
 * El metodo que se sigue es el siguiente, se utiliza una pila de operadores en la cual se almacena por
 * precedencia de importancia cada operador que venga en la expresion a evaluar, y se utiliza una pila
 * posfija en la cual se resuelven las operaciones conforme lleguen a la pila, de al forma que se garantiza
 * la precedencia de los operadores y la precision en el calculo de la expresion.
 * @author Camilo Nova
 */
public class DemultiplexorExpresion {
	
	/**
	 * EOL representa el fin de linea el cual se utiliza para determinar en que parte
	 * termina la sentencia.
	 */
    private static final int EOL     = 0;
    
    /**
     * Representa el operando de la expresion "911"
     */
    private static final int VALOR   = 1;
    
    /**
     * Representa el parentesis que abre "("
     */
    private static final int ABRE	 = 2;
    
    /**
     * Representa el parentesis que cierra ")"
     */
    private static final int CIERRA  = 3;
    
    /**
     * Representa el operador exponencial "^"
     */
    private static final int EXP     = 4;
    
    /**
     * Representa el operador multiplicador "*"
     */
    private static final int MULT    = 5;
    
    /**
     * Representa el operador de division "/"
     */
    private static final int DIV     = 6;
    
    /**
     * Representa el operador de suma "+"
     */
    private static final int SUMA    = 7;
    
    /**
     * Representa el operador de resta "-"
     */
    private static final int RESTA   = 8;
    
    /**
     * Representa la tabla de precedencia de los operadores, aui es donde se asigna que precedencia
     * tiene cada operador en la pila.
     */
    private static PrecedenciaOperadores[] tablaPrecedencia = new PrecedenciaOperadores[] {
    		new PrecedenciaOperadores(0, -1),  // EOL 
			new PrecedenciaOperadores(0, 0),   // VALOR
			new PrecedenciaOperadores(100, 0), // ABRE
			new PrecedenciaOperadores(0, 99),  // CIERRA
			new PrecedenciaOperadores(6, 5),   // EXP
			new PrecedenciaOperadores(3, 4),   // MULT
			new PrecedenciaOperadores(3, 4),   // DIV
			new PrecedenciaOperadores(1, 2),   // SUMA
			new PrecedenciaOperadores(1, 2)    // RESTA
    };

    /**
     * Representa la pila de operadores
     */
    private Stack pilaOperadores;

    /**
     * Representa la pila posfija
     */
    private Stack pilaPosfija;
    
    /**
     * Representa el Tokenizer de la expresion
     */
    private StringTokenizer str;

    /**
     * Esta clase representa la precedencia de los operadores que esta dada por
     * un identificador de simbolo de entrada y otro de cima de la pila.
     * @author Camilo Nova
     */
    private static class PrecedenciaOperadores {
    	
    	/**
    	 * Representa el simbolo de entrada
    	 */
        public int simboloEntrada;
        
        /**
         * Representa la cima de la pila
         */
        public int cimaPila;

        /**
         * Constructor de la clase, asigna los valores pasados por parametro
         * @param simboloEntrada		Simbolo de Entrada
         * @param cimaPila				Simbolo de Cima de la Pila
         */
        public PrecedenciaOperadores(int simboloEntrada, int cimaPila) {
            this.simboloEntrada = simboloEntrada;
            this.cimaPila = cimaPila;
        }
    }

    /**
     * Esta clase representa los Token de la expresion, de tal manera que convierte
     * un token de la forma "+" a su valor numerico para que pueda ser evaluado
     * por las pilas.
     * @author Camilo Nova
     */
    private static class Token {
    	
    	/**
    	 * Representa el tipo del token
    	 */
        private int tipo = EOL;
        
        /**
         * Representa el valor del Token
         */
        private double valor = 0;

        /**
         * Constructor de la clase.
         *
         */
        public Token() {
            this(EOL);
        }
        
        /**
         * Constructor de la clase.
         * @param t		Tipo del token
         */
        public Token(int t) {
            this(t, 0);
        }
        
        /**
         * Constructor de la clase.
         * @param t		Tipo del Token
         * @param v		Valor del Token
         */
        public Token(int t, double v) {
            tipo = t;
            valor = v;
        }
        
        /**
         * Retorna el tipo del Token
         * @return	Tipo del Token
         */
        public int getTipo() {
            return tipo;
        }
        
        /**
         * Retorna el valor del Token
         * @return	Valor del Token
         */
        public double getValor() {
            return valor;
        }
    }    

    /**
     * Esta clase evalua el String Tokenizer enviado por parametro, y convierte
     * a Tokens sus operadores, con el fin de evaluar la expresion.
     * @author Camilo Nova
     */
    private static class EvaluadorToken {
        
    	/**
    	 * Representa el Conjunto de Tokens
    	 */
    	private StringTokenizer str;
        
    	/**
    	 * Constructor de la clase, asigna el valor enviado por parametro.
    	 * @param is	StringTokenizer con los tokens
    	 */
        public EvaluadorToken(StringTokenizer is) {
            str = is;
        }
        
        /**
         * Encuentra el Token siguiente, salta los espacios, y lo retorna.
         * Para el Token VALOR, Asigna el valor procesado a valorActual.
         * Imprime error si el valor no se reconoce.
         */
        public Token getToken() {
            double valorActual;           

            if(!str.hasMoreTokens())
                return new Token();

            String s = str.nextToken();
            if(s.equals(" ")) return getToken();
            if(s.equals("^")) return new Token(EXP);
            if(s.equals("/")) return new Token(DIV);
            if(s.equals("*")) return new Token(MULT);
            if(s.equals("(")) return new Token(ABRE);
            if(s.equals(")")) return new Token(CIERRA);
            if(s.equals("+")) return new Token(SUMA);
            if(s.equals("-")) return new Token(RESTA);

            try { 
            	valorActual = Double.parseDouble(s);
            }
            catch(NumberFormatException e) {
                System.err.println("Error en Numero!!!");
                return new Token();
            }
            
            return new Token(VALOR, valorActual);
        }
    }
        
    /**
     * Constructor de la clase. Crea las pilas y convierte a tokens
     * el parametro recibido.
     * @param s		Cadena a evaluar
     */
    public DemultiplexorExpresion(String s) {
        pilaOperadores = new Stack();
        pilaPosfija = new Stack();

        str = new StringTokenizer(s, "+*-/^() ", true);

        pilaOperadores.push(new Integer(EOL));
    }

    /**
     * Metodo que calcula el valor de la expresion, llamando a los
     * metodos de esta clase.
     * @return	Valor numerico de la expresion.
     */
    public double getValor() {
        EvaluadorToken tok = new EvaluadorToken(str);
        Token ultimoToken;

        do {
            ultimoToken = tok.getToken();
            procesarToken(ultimoToken);
        } while(ultimoToken.getTipo() != EOL);

        if(pilaPosfija.isEmpty()) {
            System.err.println("Falta un Operador!");
            return 0;
        }

        double resultado = posfijaTopAndPop();
        
        if(!pilaPosfija.isEmpty())
            System.err.println("Warning: Falta operador!");

        return resultado;
    }

    /**
     * Luego de que el Token sea leido, se utiliza la precedencia
     * de los operadores para procesarlo, si existen parentesis
     * abiertos son detectados aqui.
     */
    private void procesarToken(Token ultimoToken){
        int topOp;
        int lastType = ultimoToken.getTipo( );
   
        switch(lastType) {
          case VALOR:
          	pilaPosfija.push(new Double(ultimoToken.getValor()));
            return;

          case CIERRA:
            while((topOp = operadoresTop()) != ABRE && topOp != EOL)
                binaryOp(topOp);
            if(topOp == ABRE)
                pilaOperadores.pop();
            else
                System.err.println("Falta un ABRE");
            break;

          default:    // Operador
            while(tablaPrecedencia[lastType].simboloEntrada <= tablaPrecedencia[topOp = operadoresTop()].cimaPila)
                binaryOp(topOp);
            if(lastType != EOL)
                pilaOperadores.push(new Integer(lastType));
            break;
        }
    }

    /**
     * Retorna el resultado de la pila posfija, si la pila esta
     * vacia retorna cero e imprime error.
     */
    private double getTop() {
        if (pilaPosfija.isEmpty()) {
            System.err.println("Falta un Operador");
            return 0;
        }
        return posfijaTopAndPop();
    }

    /**
     * Procesa un operador tomando dos elementos de la pila
     * posfija, aplica el operador y retorna el resultado
     * a la pila.
     */
    private void binaryOp(int topOp) {
        double rhs = getTop();
        double lhs = getTop();
        
        if( topOp == ABRE ) {
            System.err.println("Parentesis desbalanceado");
            pilaOperadores.pop();
            return;
        }

        if(topOp == EXP)
            pilaPosfija.push(new Double(Math.pow(lhs, rhs)));
        else if(topOp == SUMA)
        	pilaPosfija.push(new Double(lhs + rhs));
        else if(topOp == RESTA)
        	pilaPosfija.push(new Double(lhs - rhs));
        else if(topOp == MULT)
        	pilaPosfija.push(new Double(lhs * rhs));
        else if(topOp == DIV)
            if(rhs != 0 )
            	pilaPosfija.push(new Double(lhs / rhs));
            else {
                System.err.println("Division por cero");
                pilaPosfija.push(new Double(lhs));
            }
        pilaOperadores.pop();
    }

    /**
     * Retorna el elemento de la cima de la pila posfija
     * @return		Elemento de la cima
     */
    private double posfijaTopAndPop() {
        return ((Double)(pilaPosfija.pop())).doubleValue();
    }

    /**
     * Retorna el elemento de la cima de la pila de operadores
     * @return		Elemento de la cima
     */
    private int operadoresTop() {
        return ((Integer)(pilaOperadores.peek())).intValue();
    }
}