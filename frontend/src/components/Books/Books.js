import React, {useEffect, useState} from "react";
import axios from "axios";
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import Button from '@mui/material/Button';

export default function Books() {
    const [books, setBooks] = useState([]); // State to store book data
    const [selectedBook, setSelectedBook] = useState(null); // State to store selected book details

    useEffect(() => {
        loadBooks(); // Load books when the component mounts
    }, []);

    const loadBooks = async () => {
        try {
            const result = await axios.get("http://localhost:8081/books");
            setBooks(result.data); // Set the fetched books to state
        } catch (error) {
            console.error("Error loading books:", error);
        }
    };

    const loadBookDetails = async (bookId) => {
        try {
            const result = await axios.get(`http://localhost:8081/books/${bookId}`);
            setSelectedBook(result.data); // Set the selected book details
            console.log(result.data); // Log the selected book details for debugging
        } catch (error) {
            console.error("Error loading book details:", error);
        }
    };

    return (
        <div>
            <TableContainer component={Paper}>
                <Table sx={{ minWidth: 650 }} aria-label="books table">
                    <TableHead>
                        <TableRow>
                            <TableCell>Title</TableCell>
                            <TableCell align="center">Author</TableCell>
                            <TableCell align="center">Quantity</TableCell>
                            <TableCell align="center">Action</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {books.length > 0 ? (
                            books.map((book) => (
                                <TableRow key={book.id}>
                                    <TableCell>{book.title}</TableCell>
                                    <TableCell align="center">{book.author}</TableCell>
                                    <TableCell align="center">{book.quantity}</TableCell>
                                    <TableCell align="center">
                                        <Button variant="contained" onClick={() => loadBookDetails(book.id)}>
                                            Details
                                        </Button>
                                    </TableCell>
                                </TableRow>
                            ))
                        ) : (
                            <TableRow>
                                <TableCell colSpan={6} align="center">
                                    No books available
                                </TableCell>
                            </TableRow>
                        )}
                    </TableBody>
                </Table>
            </TableContainer>

            {selectedBook && (
                <div style={{ marginTop: "20px" }}>
                    <h2>Book Details</h2>
                    <p><strong>Title:</strong> {selectedBook.title}</p>
                    <p><strong>Author:</strong> {selectedBook.author}</p>
                    <p><strong>ISBN:</strong> {selectedBook.isbn}</p>
                    <p><strong>Quantity:</strong> {selectedBook.quantity}</p>
                    <p><strong>Added At:</strong> {new Date(selectedBook.addedAt).toLocaleString()}</p>
                </div>
            )}
        </div>
    );
}
