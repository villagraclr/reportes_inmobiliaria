package cl.desafiolatam.rest.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cl.desafiolatam.rest.jwt.JwtUtils;
import cl.desafiolatam.rest.models.User;
import cl.desafiolatam.rest.services.IUserService;
import cl.desafiolatam.rest.vo.LoginRequest;
import cl.desafiolatam.rest.vo.LoginResponse;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private IUserService userService;
	
	@PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> registerUser(@RequestBody User user) {
		System.out.println(user.toString());
        if (userService.existsByUsername(user.getUsername())) {
            // Username already exists, returning a bad request with an error message
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Username already exists");
        }

        // Determine which role is being assigned
        boolean isAdmin = user.getRoles().stream()
                              .anyMatch(role -> "ROLE_ADMIN".equals(role.getName()));
        System.out.println("isAdmin ["+isAdmin+"]");
        // Save the user with the appropriate role
        if (isAdmin) {
            userService.saveUserWithAdminRole(user);
        } else {
            userService.saveWithUserRole(user);
        }

        // Return a response indicating the user was created successfully
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("User registered successfully");
    }

	@PostMapping("/login")
    public ResponseEntity<?> createJwtToken(@RequestBody LoginRequest loginRequest) throws Exception {
        
		/*debug: System.out.println("username "+ loginRequest.getUserName()); */
		//crea objeto de autenticacion (setup the username/pwd)
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        }catch (DisabledException e) {
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("msg", "Cuenta desactivada");
            errorMap.put("status", false);
            return new ResponseEntity<Object>(errorMap, HttpStatus.LOCKED);

        } catch (BadCredentialsException e) {
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("msg", "Credenciales inválidas");
            errorMap.put("status", false);
            return new ResponseEntity<Object>(errorMap, HttpStatus.UNAUTHORIZED);

        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //autentica username y pswd en la base de datos
        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        //si el nombre de usuario y contraseña corresponden, se crea el token
        final String jwtToken = jwtUtils.generateToken(userDetails);
        final List<String> roles = authentication.getAuthorities()//se obtiene rol o roles del usuario
                .stream()
                .map(role-> role.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new LoginResponse(jwtToken, userDetails.getUsername(), roles));
    }
}
