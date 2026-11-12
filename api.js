const BASE = 'http://localhost:8080/api';
export async function request(path, options={}) {
  const response = await fetch(BASE + path, {
    headers: {'Content-Type':'application/json', ...(options.headers || {})},
    ...options
  });
  if (!response.ok) {
    const message = await response.text();
    throw new Error(message || `Request failed: ${response.status}`);
  }
  if (response.status === 204) return null;
  return response.json();
}
